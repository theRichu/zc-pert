require "yaml"
require 'nokogiri'
require 'base_main_frame'
require 'bean/start_up_bean'
require 'bean/project_bean'
require 'xml_parser'
include Wx

class InterfaceViewerFrame < BasicMainFrame
  def initialize
    super
    set_icon(Icon.new("./res/icon/iv.png"))  # set the icon for the interface viewer
    @opened, @item_position, @item_and_file = Hash.new, Hash.new, Hash.new # the path of the opened file
    # add the auinotebook ctrl manually to the frame, 
    init_auinotebook
    # initialize the window position and size and load the recent files
    init_frame_contents
    # initialize the style of the outline
    init_outline
    # handlers for the events 
    frame_close_event_handler # the close event of the main window
    project_tree_dbclick_event_handler # the event on the project tree item to open the file
    file_close_event_handler # the event handler for auinotebook page closing
    auipage_changed_event_handler
    outline_click_event_handler
    search_click_event_handler
    menu_event_handler
    tool_event_handler
  end
  
  def init_auinotebook
    #for the wxruby does not support the xrc handler for auinotebook control
    sizer = BoxSizer.new(VERTICAL)
    @notebook = AuiNotebook.new(@p_editor)
    sizer.add(@notebook, 1, GROW|ALL, 0)
    @p_editor.set_sizer(sizer)
    @p_editor.layout
  end
  
  def init_frame_contents
    start_up = YAML.load_file(StartUpBean::FILE_PATH)
    self.set_size(Rect.new(start_up.rect[0],start_up.rect[1],start_up.rect[2],start_up.rect[3]))
    @sw_dir.set_sash_position(start_up.sashes[0])
    @sw_editor.set_sash_position(start_up.sashes[1])
    if proj_def = start_up.proj_def then
      init_project_tree(proj_def)
    end
    if files = start_up.files then
      load_recent_files(files)
      # init the outline
      rebuild_outline
    end
  end
  
  def init_project_tree(proj_def)
    project = YAML.load_file(proj_def)
    image_list = ImageList.new(16, 16)
    image_list.add(Bitmap.from_image(Image.new("./res/icon/iv.png"))) # for root
    image_list.add(Bitmap.from_image(Image.new("./res/icon/folder.png"))) # for folder closed
    image_list.add(Bitmap.from_image(Image.new("./res/icon/folder_open.png"))) # for folder opened
    image_list.add(Bitmap.from_image(Image.new("./res/icon/file.png"))) # for file
    @dc_proj.set_image_list(image_list)
    @dc_proj.add_root(project.name, 0, 0, project.path)
    fill_project_tree(project.path, @dc_proj.get_root_item)
  end
  
  def load_recent_files(files)
    files.each do |key, value|
      open_file(value, key)
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
  
  def open_file(file_name, path)
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
  
  def init_outline
    image_list = ImageList.new(16, 16)
    image_list.add(Bitmap.from_image(Image.new("./res/icon/xml_r.png"))) # for root
    image_list.add(Bitmap.from_image(Image.new("./res/icon/xml_n.png"))) # for folder closed
    @tc_outline.set_image_list(image_list)
  end
  
  def rebuild_outline
    # initialize the outline view for file
    page_index = @notebook.get_selection
    if page_index >= 0 then
      parser = XMLParser.new(@notebook.get_page(page_index).get_name, @tc_outline)
      @tc_outline.delete_all_items
      @item_position = parser.parse
      # @tc_outline.expand_all
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
      start_up = StartUpBean.new(rect, sashes, @opened, "./res/yaml/test.yaml")
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
      open_file(file_name, path)
    end
  end
  
  def outline_click_event_handler
    evt_tree_sel_changed(@tc_outline) do |evt|
      pos = @item_position[evt.get_item]
      editor = @notebook.get_page(@notebook.get_selection)
      editor.set_selection(pos[0], pos[1])
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
        s_key = @tc_key.get_value
        @item_and_file.values.each do |value|
          if value.include?(s_key) then
            item = @item_and_file.key(value)
            @dc_proj.select_item(item)
            @dc_proj.ensure_visible(item)
          end
        end
      end
    end
  end
  
  def menu_event_handler
    evt_menu(@mi_newproj) do |evt|
      p evt.methods
    end
    evt_menu(@mi_openproj) do |evt|
      p evt.methods
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
      p evt.methods
    end 
    evt_menu(@mi_about) do |evt|
      p evt.methods
    end
  end
  
  def tool_event_handler
    evt_tool(@t_newproj) do |evt|
      p evt.methods
    end
    evt_tool(@t_open) do |evt|
      p evt.methods
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
  end
  
  def on_save_file
    selected = @notebook.get_selection
    editor = @notebook.get_page(selected)
    if editor.get_modify then
      file_path = editor.get_name
      editor.save_file(file_path)
      page_label = @notebook.get_page_text(selected)
      if page_label.include?("*") then
        @notebook.set_page_text(selected, page_label.delete("*"))
      end
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
  
end

