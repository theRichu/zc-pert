require "wx"
require "yaml"
require File.dirname(__FILE__) + '/base_main_frame'
require File.dirname(__FILE__) + '/interface_file_finder'
require File.dirname(__FILE__) + '/xml_parser'
require File.dirname(__FILE__) + '/search_view_history'
include Wx 

class InterfaceViewerFrame < BaseMainFrame
  def initialize
    super
    
    @text_inter_id.set_value("cp1033i01")
    @text_look_in.set_value("E:/workspace/hostresponse")
    @item_data = nil
    @xml_parser = nil
    # initialize the number of the column of the grid
    @grid_fields.create_grid(0, 3)
    @grid_fields.disable_drag_grid_size
    @grid_fields.set_col_label_size(20);
    @grid_fields.set_row_label_size(50);
    @grid_fields.set_col_label_value(0, "Field"); 
    @grid_fields.set_col_label_value(1, "Type"); 
    @grid_fields.set_col_label_value(2, "Value");
    
    #load the history into the list box
    load_history
    
    # register_event_handler
    register_event_handler
  end
  
  def parse_xml(xml_path)
    if xml_path then
      @xml_parser = XMLParser.new(xml_path)
      # clear the data in the tree
      @tree_fields.delete_all_items
      # clear the data in the grid
      @grid_fields.delete_rows(0, @grid_fields.get_number_rows)
      @xml_parser.parse(@tree_fields, @grid_fields, @btn_save)
      @tree_fields.expand_all
    end
  end
  
  def reset_btn_save_label
    @btn_save.set_label("Save Change")
  end
  
  def update_grid(selections)
    return unless selections
    # clear the data in the grid
    @grid_fields.delete_rows(0, @grid_fields.get_number_rows)
    # insert the new data into the grid
    selections.each do |item_id|
      items = [item_id]
      until items.empty?
        curt_item = items.shift
        item_elem = @tree_fields.get_item_data(curt_item)
        if item_elem then # if is a node
          # add it to the grid
          GridHelper.add(@grid_fields, curt_item, item_elem, @tree_fields, @btn_save)
        else # if it is has child(ren)
          # push all the children to the items array
          @tree_fields.get_children(curt_item).each do |child_item|
            items.push(child_item)
          end
        end
      end
    end
  end
  
  def update_history(history)
    all_history = YAML.load_file(SearchViewHistory::PATH)
    is_include = false
    if all_history then
      all_history.each do |hi|
        if history == hi then
          is_include = true
          # update the rate for this history
          hi.rate += 1
          break
        end
      end 
    end
    # if no history right now befor, create it
    unless all_history then
      all_history = []
    end
    # add it into the history
    unless is_include then
      all_history<<history
    end
    # store the history to the file
    File.open(SearchViewHistory::PATH, "w") do |io|
      YAML.dump(all_history,io)
    end
    # load the dated history to the list box
    load_history
  end
  
  def load_history
    all_history = YAML.load_file(SearchViewHistory::PATH)
    @listbox_history.set(all_history.sort.collect {|x| x.to_s })
  end

  def check_text_value(text_ctrl)
    text_value = text_ctrl.get_value.strip
    if nil == text_value || 0 == text_value.length  then
      return nil
    else
      return text_value
    end
  end

  def check_path(text_ctrl)
    path = text_ctrl.get_value.strip
    if nil == path || 0 == path.length then
      return nil
    elsif FileTest.directory?(path) || FileTest.file?(path) then
      return path.gsub(/\\/, "/")
    else
      return nil
    end
  end
  
  def register_event_handler
    # event handler for the select dir button
    evt_button(@btn_select_dir) do |evt|
      dir_dialog = Wx::DirDialog.new(self)
      if (ID_OK == dir_dialog.show_modal) then
        @text_look_in.set_value(dir_dialog.get_path)
      end
    end
    
    # event handler for the select file button
    evt_button(@btn_select_file) do |evt|
      file_dialog = Wx::FileDialog.new(self)
      if (ID_OK == file_dialog.show_modal) then
        @text_file_path.set_value(file_dialog.get_path)
      end
    end
    
    # event handler for view file button
    evt_button(@btn_view_file) do |evt|
      # update the label for save button
      reset_btn_save_label
      if file_path = check_path(@text_file_path) then
        if file_path.downcase.include?(InterfaceFileFinder::RQ.downcase) || file_path.downcase.include?(InterfaceFileFinder::RS.downcase) then
          parse_xml(file_path)
          # create history object
          history = SearchViewHistory.new(SearchViewHistory::VIEW_FILE, "", "", "", file_path)
          # update the history
          update_history(history)
        else
          @status_bar.set_status_text(%Q|File Type Error: The file "#{File.basename(file_path)}" you selected is not a valid host request file or response file, please re-select!|)
        end
     end
    end
    
    # event handler for search button
    evt_button(@btn_search) do |evt|
      # update the label for save button
      reset_btn_save_label
      # get information for search
      inter_id, look_in, file_type = check_text_value(@text_inter_id), check_path(@text_look_in), radio_rs.get_value ? InterfaceFileFinder::RS : InterfaceFileFinder::RQ
      if inter_id && look_in then
        inter_finder = InterfaceFileFinder.new(look_in, @status_bar)
        xml_path = inter_finder.find(inter_id, file_type)
        if xml_path then
          @status_bar.set_status_text(%Q|Search Rsult: #{xml_path.to_s}.|)
          parse_xml(xml_path)
          # create history object
          history = SearchViewHistory.new(SearchViewHistory::SEARCH, inter_id, file_type, look_in)
          # update the history
          update_history(history)
        else
          @status_bar.set_status_text(%Q|Search Rsult: Can not find the "#{file_type}" file of interface "#{inter_id}" in the "#{look_in}."|)
        end
      end
    end
    
    # event handler for tree selection
    evt_tree_sel_changed(@tree_fields) do |evt|
      if selections = @tree_fields.get_selections then
        @status_bar.set_status_text(selections.join(", "))
        update_grid(selections)
      end
    end
    
    # event handler for save changes button
    evt_button(@btn_save) do |evt|
      if @btn_save.get_label.include?("*") then
        @xml_parser.save
        reset_btn_save_label
        @status_bar.set_status_text(%Q|Your changes have bee saved At #{Time.new.to_s}.|)
      end
    end
    
    #event handler for list box dbclick
    evt_listbox_dclick(@listbox_history) do |evt|
      hi = evt.get_string
      hi_arr = hi.split(": ")
      if hi_arr[0] == SearchViewHistory::SEARCH then
        search_hi_arr = hi_arr[1].split(", ")
        @text_inter_id.set_value(search_hi_arr[0])
        @radio_rq.set_value(search_hi_arr[1] == InterfaceFileFinder::RQ)
        @radio_rs.set_value(search_hi_arr[1] == InterfaceFileFinder::RS)
        @text_look_in.set_value(search_hi_arr[2])
      else
        @text_file_path.set_value(hi_arr[1])
      end
    end
  end

end