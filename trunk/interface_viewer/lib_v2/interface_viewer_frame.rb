require "yaml"
require 'nokogiri'
require 'base_main_frame'
require 'bean/start_up_bean'
require 'bean/project_bean'
require 'bean/item_position'
require 'xml_parser'
require 'new_proj_dialog'
include Wx

class InterfaceViewerFrame < BasicMainFrame
  def initialize
    super
    @opened, @item_position, @item_and_file = Hash.new, ItemPosition.new, Hash.new # the path of the opened file
    # initialize the size, position, sashes position of the frame
    init_frame_style
    # add the auinotebook ctrl manually to the frame, 
    init_auinotebook_style
    # initialize the style of the project tree
    init_project_tree_style
    # initialize the style of the outline
    init_outline_style
    # initialize the window position and size and load the recent files
    init_frame_contents
    # handlers for the events 
    frame_close_event_handler # the close event of the main window
    project_tree_dbclick_event_handler # the event on the project tree item to open the file
    file_close_event_handler # the event handler for auinotebook page closing
    auipage_changed_event_handler # the event handler for user choosing another file, to rebuild the outline
    outline_click_event_handler # user cilck on the outline, to locate the element
    search_click_event_handler # user search file(s) in the project
    menu_event_handler # user click on the menu item
    tool_event_handler # user click on the tool item
  end
  
  def init_auinotebook_style
    #for the wxruby does not support the xrc handler for auinotebook control
    sizer = BoxSizer.new(VERTICAL)
    @notebook = AuiNotebook.new(@p_editor)
    sizer.add(@notebook, 1, GROW|ALL, 0)
    @p_editor.set_sizer(sizer)
    @p_editor.layout
  end
  
  def init_frame_style
    set_icon(Icon.new("./res/icon/iv.png"))  # set the icon for the interface viewer
    @start_up = YAML.load_file(StartUpBean::FILE_PATH)
    self.set_size(Rect.new(@start_up.rect[0],@start_up.rect[1],@start_up.rect[2],@start_up.rect[3]))
    @sw_dir.set_sash_position(@start_up.sashes[0])
    @sw_editor.set_sash_position(@start_up.sashes[1])
  end
  
  def init_frame_contents
    if proj_def = @start_up.proj_def then
      init_project_tree(proj_def)
    end
    if files = @start_up.files then
      load_recent_files(files)
      # init the outline
      rebuild_outline
    end
  end
  
  def init_project_tree_style
    image_list = ImageList.new(16, 16)
    image_list.add(Bitmap.from_image(Image.new("./res/icon/iv.png"))) # for root
    image_list.add(Bitmap.from_image(Image.new("./res/icon/folder.png"))) # for folder closed
    image_list.add(Bitmap.from_image(Image.new("./res/icon/folder_open.png"))) # for folder opened
    image_list.add(Bitmap.from_image(Image.new("./res/icon/file.png"))) # for file
    @dc_proj.set_image_list(image_list)
  end
  
  def init_project_tree(proj_def)
    @proj_path = proj_def
    project = YAML.load_file(proj_def)
    @dc_proj.delete_all_items
    @dc_proj.add_root(project.name, 0, 0, project.path)
    fill_project_tree(project.path, @dc_proj.get_root_item)
  end
  
  def load_recent_files(files)
    files.each do |key, value|
      on_open_file(value, key)
    end
  end
  
  def fill_project_tree(base_dir, root_item)
    # scan the response file in the directory
    dir_list = [base_dir]
    parent_list = [root_item]
    until dir_list.empty?
      # get the dir from the list
      current_dir = dir_list.shift
      parent = parent_list.shift
      current_dir << "/" unless current_dir[-1].chr == "/"
      @statusbar.set_status_text("Scanning files in the project: #{current_dir}")
      # begin to traversa this dir, if find, reuslt will be the path, otherwise nil
      Dir.foreach(current_dir) do |child|
        # exclude the ., .., svn, web-inf
        next if child == "." || child == ".." || child.include?("\.svn") || child.include?("WEB-INF")
        # form the path
        child_path = current_dir + child
        if FileTest.directory?(child_path) then
          dir_list.push(child_path)
          # put directory into the project tree
          item = @dc_proj.append_item(parent, child, 1, 2, child_path)
          parent_list.push(item)
          next
        end
        if FileTest.file?(child_path) && child_path.include?("\.xrc") then
          # put file name into the project tree
          file_item = @dc_proj.append_item(parent, child, 3, 3, child_path)
          @item_and_file[file_item] = child
        else
          next
        end
      end
      
      # if it does not have children, do not display it
      unless @dc_proj.item_has_children(parent) then
        parent_parent = @dc_proj.get_item_parent(parent)
        @dc_proj.delete(parent)
        if parent_parent && !@dc_proj.item_has_children(parent_parent) then
          @dc_proj.delete(parent_parent)
        end
      end
    end
  end
  
  def on_open_file(file_name, path)
    return nil unless FileTest.file?(path)
    return nil if @opened.has_key?(path)  
    # add one page for the auinotebook
    @opened[path] = file_name
    editor = create_editor
    editor.set_name(path)
    @notebook.add_page(editor, file_name)
    # load file content to the editor
    format_xml_file(path)
    editor.load_file(path)
    # set the new open file in focus
    @notebook.set_selection(@notebook.get_page_count - 1)
    # add the modification listener
    evt_stc_modified(editor) do |evt|
      selected = @notebook.get_selection
      editor = @notebook.get_page(selected)
      if editor.get_modify then
        page_label = @notebook.get_page_text(selected)
        unless page_label.include?("*") then
          @notebook.set_page_text(selected, page_label + "*")
        end
      end
    end
  end
  
  def init_outline_style
    image_list = ImageList.new(16, 16)
    image_list.add(Bitmap.from_image(Image.new("./res/icon/xml_r.png"))) # for root
    image_list.add(Bitmap.from_image(Image.new("./res/icon/xml_n.png"))) # for folder closed
    @tc_outline.set_image_list(image_list)
  end
  
  def rebuild_outline
    # initialize the outline view for file
    page_index = @notebook.get_selection
    if page_index >= 0 then
      editor = @notebook.get_page(page_index)
      path_xpath = editor.get_name
      px = path_xpath.split("|")
      xml_path = px[0]
      xpath = px[1]
      parser = XMLParser.new(xml_path, @tc_outline, @item_position)
      @tc_outline.delete_all_items
      parser.parse()
      if xpath && xpath.length > 0 then
        item = @item_position.get_item(path_xpath)
      else
        item = @tc_outline.get_root_item
        @tc_outline.expand(item)
      end
      @tc_outline.select_item(item)
      @tc_outline.ensure_visible(item)
    end
  end
  
  def format_xml_file(path)
    xml_doc = Nokogiri::XML(File.open(path))
    xml_file = File.open(path, "w");
    begin
      xml_doc.write_xml_to(xml_file, :encoding => xml_doc.encoding, :indent => 2)
    rescue Exception
      return false
    ensure
      xml_file.close
    end
  end
  
  def create_editor
    editor = StyledTextCtrl.new(@notebook)
    # set default font
    font = Font.new(9, TELETYPE, NORMAL, NORMAL)
    editor.style_set_font(STC_STYLE_DEFAULT, font);
    # set to show the line number and the width of the line number
    line_num_margin = editor.text_width(STC_STYLE_LINENUMBER, "_99999")
    editor.set_margin_width(0, line_num_margin)
    # set the highlight    
    editor.set_lexer(STC_LEX_XML)
    editor.style_clear_all
    editor.style_set_foreground(0, BLACK)
    editor.style_set_foreground(1, Colour.new(163,21,21))
    editor.style_set_foreground(3, BLUE)
    editor.style_set_foreground(6, RED)
    editor.set_style_bits(7)
    editor.set_property("asp.default.language", "1")
    editor.set_property("fold.html", "0")
    editor.set_property("fold", "1")
    editor.set_property("fold.compact", "1")
    # set indents
    editor.set_tab_width(2)
    editor.set_use_tabs(false)
    editor.set_tab_indents(true)
    editor.set_back_space_un_indents(true)
    editor.set_indent(2)
    
    return editor
  end
  
  def frame_close_event_handler
    evt_close do
      rect = [get_screen_rect.x,get_screen_rect.y,get_screen_rect.width,get_screen_rect.height]
      sashes = [@sw_dir.get_sash_position, @sw_editor.get_sash_position]
      start_up = StartUpBean.new(rect, sashes, @opened, @proj_path)
      File.open(StartUpBean::FILE_PATH, "w") do |io|
        YAML.dump(start_up,io)
      end
      destroy
    end
  end
  
  def project_tree_dbclick_event_handler
    evt_tree_item_activated(@dc_proj) do |evt|
      file_name = @dc_proj.get_item_text(evt.get_item)
      path = @dc_proj.get_item_data(evt.get_item)
      on_open_file(file_name, path)
      @statusbar.set_status_text("#{file_name} is opened!")
    end
  end
  
  def outline_click_event_handler
    evt_tree_sel_changed(@tc_outline) do |evt|
      editor = @notebook.get_page(@notebook.get_selection)
      path = editor.get_name.split("|")[0]
      xpath = @tc_outline.get_item_data(evt.get_item).path
      key = path + "|" + xpath
      pos = @item_position.get_pos(key)
      editor.set_selection(pos[0], pos[1])
      editor.set_name(key) # to store the current selection
      @statusbar.set_status_text(@tc_outline.get_item_text(evt.get_item))
    end
  end
  
  def file_close_event_handler
    evt_auinotebook_page_close(@notebook) do |evt| 
      # now we just can get the caption of the tab, and we should use it to query the path
      @opened.delete(@notebook.get_page(evt.get_old_selection).get_name)
    end
  end
  
  def auipage_changed_event_handler
    evt_auinotebook_page_changed(@notebook) do |evt|
      rebuild_outline
    end
  end
  
  def search_click_event_handler
    evt_button(@b_search) do |evt|
      unless @tc_key.is_empty then
        count = 0
        # collapse to clear the previous view
        @dc_proj.collapse_all
        s_key = @tc_key.get_value
        @item_and_file.each do |key, value|
          if value.downcase.include?(s_key.downcase) then
            @dc_proj.select_item(key)
            @dc_proj.ensure_visible(key)
            count += 1
          end
        end
        @statusbar.set_status_text("#{count} file(s) found!")
      end
    end
  end
  
  def menu_event_handler
    evt_menu(@mi_newproj) do |evt|
      on_new_proj
    end
    evt_menu(@mi_openproj) do |evt|
      on_open_proj
    end
    evt_menu(@mi_save) do |evt|
      on_save_file
    end
    evt_menu(@mi_exit) do |evt|
      close()
    end
    evt_menu(@mi_undo) do |evt|
      on_undo
    end
    evt_menu(@mi_redo) do |evt|
      on_redo
    end
    evt_menu(@mi_copy) do |evt|
      on_copy
    end
    evt_menu(@mi_cut) do |evt|
      on_cut
    end
    evt_menu(@mi_paste) do |evt|
      on_paste
    end
    evt_menu(@mi_find) do |evt|
      on_search_in_file
    end 
    evt_menu(@mi_about) do |evt|
      Wx::about_box( :name => 'Interface Viewer', :version => '1.0.2', :developers => ['Tony Wang'] )
    end
  end
  
  def tool_event_handler
    evt_tool(@t_newproj) do |evt|
      on_new_proj
    end
    evt_tool(@t_open) do |evt|
      on_open_proj
    end
    evt_tool(@t_save) do |evt|
      on_save_file
    end
    evt_tool(@t_undo) do |evt|
      on_undo
    end
    evt_tool(@t_redo) do |evt|
      on_redo
    end
    evt_tool(@t_cut) do |evt|
      on_cut
    end
    evt_tool(@t_copy) do |evt|
      on_copy
    end
    evt_tool(@t_paste) do |evt|
      on_paste
    end
    evt_tool(@t_find) do |evt|
      on_search_in_file
    end
  end
  
  def on_save_file
    selected = @notebook.get_selection
    editor = @notebook.get_page(selected)
    if editor.get_modify then
      file_path = editor.get_name.split("|")[0]
      editor.save_file(file_path)
      page_label = @notebook.get_page_text(selected)
      if page_label.include?("*") then
        @notebook.set_page_text(selected, page_label.delete("*"))
      end
      @statusbar.set_status_text("File have been saved to #{file_path}!")
    end
  end
  
  def on_undo
    editor = @notebook.get_page(@notebook.get_selection)
    if editor.can_undo then
      editor.undo
    end
  end
  
  def on_redo
    editor = @notebook.get_page(@notebook.get_selection)
    if editor.can_redo then
      editor.redo
    end
  end
  
  def on_cut
    editor = @notebook.get_page(@notebook.get_selection)
    selected = editor.get_selected_text
    if selected.length > 0 then
      editor.cut
    end
  end
  
  def on_copy
    editor = @notebook.get_page(@notebook.get_selection)
    selected = editor.get_selected_text
    if selected.length > 0 then
      editor.copy
    end
  end
  
  def on_paste
    editor = @notebook.get_page(@notebook.get_selection)
    if editor.can_paste then
      editor.paste
    end
  end
  
  def on_open_proj
    file_dialog = FileDialog.new(self, "Choose a project", "./res/project/", "", "*.proj")
    if (ID_OK == file_dialog.show_modal) then
      init_project_tree(file_dialog.get_path)
    end    
  end
  
  def on_new_proj
    new_dialog = NewPorjDialog.new(self)
    if (NewPorjDialog::ID_OK == new_dialog.show_modal) then
      proj_name = new_dialog.get_proj_name
      project = ProjectBean.new(proj_name, new_dialog.get_proj_dir)
      project_def = "./res/project/#{proj_name}.proj"
      File.open(project_def, "w") do |io|
        YAML.dump(project,io)
      end
      init_project_tree(project_def)
    end 
  end
  
  def on_search_in_file
    @find_dialog = nil
    @find_dialog = FindReplaceDialog.new(self,FindReplaceData.new, "Find")
    @find_dialog.show(true)
    editor = @notebook.get_page(@notebook.get_selection)
    evt_find(@find_dialog) do |evt| 
      on_find(editor, evt.get_flags, evt.get_find_string)
    end 
    evt_find_next(@find_dialog) do |evt| 
      on_find(editor, evt.get_flags, evt.get_find_string)
    end  
  end
  
  def on_find(editor, flag, find_string)
    content = editor.get_text
    max_pos = content.length
    current_pos = editor.get_current_pos
    match_string = find_string
    if 0 == flag & FR_DOWN then
      match_string.reverse!
    end
    # remained to deal
    #    if 0 == flag & FR_WHOLEWORD then
    #      match_string = "#{match_string}"
    #    end
    if 0 == flag & FR_MATCHCASE then
      match_string = Regexp.new(match_string, Regexp::IGNORECASE)
    else
      match_string = Regexp.new(match_string)
    end
    if 1 == flag & FR_DOWN && current_pos < max_pos then
      start = content[current_pos..max_pos].index(match_string)
      if start then
        editor.set_selection(current_pos + start, current_pos + find_string.length + start)
        @statusbar.set_status_text(find_string)
      else
        @statusbar.set_status_text("'#{find_string}' Can not be found!")
      end
    end
    if 0 == flag & FR_DOWN && current_pos > 0 then
      start = content[0..current_pos].reverse.index(match_string)
      if start then
        editor.set_selection(current_pos - start + 1, current_pos - find_string.length - start + 1)
        @statusbar.set_status_text(find_string)
      else
        @statusbar.set_status_text("'#{find_string}' Can not be found!")
      end
    end
  end
  
end