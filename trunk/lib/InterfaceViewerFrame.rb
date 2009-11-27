require "wx"
require File.dirname(__FILE__) + '/BaseMainFrame'
require File.dirname(__FILE__) + '/InterfaceFileFinder'
require File.dirname(__FILE__) + '/XMLParser'
include Wx 

class InterfaceViewerFrame < BaseMainFrame
  def initialize
    super
    
    @text_inter_id.set_value("cp1033i01")
    @text_dir.set_value("Z:/webapps")
    @item_data = nil
    @xml_parser = nil
    # add the event handler for the widegts
    
    # event handler for the select dir button
    evt_button(@btn_select_dir) do |evt|
      dir_dialog = Wx::DirDialog.new(self)
      if (ID_OK == dir_dialog.show_modal) then
        @text_dir.set_value(dir_dialog.get_path)
      end
    end
    
    # event handler for search button
    evt_button(@btn_search) do |evt|
      inter_id, look_in, file_type = check_text_value(@text_inter_id), check_look_in, radio_rs.get_value ? InterfaceFileFinder::RS : InterfaceFileFinder::RQ
      if inter_id && look_in then
        inter_finder = InterfaceFileFinder.new(look_in, @status_bar)
        xml_path = inter_finder.find(inter_id, file_type)
        @status_bar.set_status_text("result: " + xml_path.to_s)
        if xml_path then
          @xml_parser = XMLParser.new(xml_path, @tree_file)
          @tree_file.delete_all_items
          @xml_parser.parse
          @tree_file.expand_all
        end
      end
      # @status_bar.set_status_text(inter_id.to_s + look_in.to_s) if inter_id && look_in
    end
    
    # event handler for tree selection
    evt_tree_sel_changed(@tree_file) do |evt|
      item_id = evt.get_item
      if evt.get_item && (@item_data = @tree_file.get_item_data(item_id)) then 
        @status_bar.set_status_text(@item_data.to_s)
        @static_text_field_name.set_label(@item_data.name)
        @text_field_value.set_value(@item_data.content)
      else
        @status_bar.set_status_text("")
        @static_text_field_name.set_label("")
        @text_field_value.set_value("")
      end
    end
    
    # event handler for save changes button
    evt_button(@btn_save) do |evt|
      new_field_value = check_text_value(@text_field_value)
      if @item_data && new_field_value then
        @item_data.content = new_field_value
        @xml_parser.save
        @status_bar.set_status_text("Saved! At " + Time.new.to_s)
      end
    end
  end
  
  def check_text_value(text_ctrl)
    text_value = text_ctrl.get_value.strip
    if nil == text_value || 0 == text_value.length  then
      return nil
    else
      return text_value
    end
  end
  
  def check_look_in
    look_in = @text_dir.get_value.strip
    if nil == look_in || 0 == look_in.length then
      return nil
    elsif FileTest.directory?(look_in) then
      return look_in.gsub(/\\/, "/")
    else
      return nil
    end
  end
  
end