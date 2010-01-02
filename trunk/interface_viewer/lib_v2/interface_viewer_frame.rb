require "yaml"
require 'nokogiri'
require 'base_main_frame'
require 'bean/start_up_bean'
require 'bean/project_bean'
include Wx

class InterfaceViewerFrame < BasicMainFrame
  def initialize
    super
    set_icon(Icon.new("./res/icon/iv.png"))  # set the icon for the interface viewer
    @opened = Hash.new # the path of the opened file
    # add the auinotebook ctrl manually to the frame, 
    init_auinotebook
    # initialize the window position and size and load the recent files
    init_frame_contentse
    # handlers for the events 
    frame_close_event_handler # the close event of the main window
    project_tree_dbclick_event_handler # the event on the project tree item to open the file
    file_close_event_handler # the event handler for auinotebook page closing
  end
  
  def init_auinotebook
    #for the wxruby does not support the xrc handler for auinotebook control
    sizer = BoxSizer.new(VERTICAL)
    @notebook = AuiNotebook.new(@p_editor)
    sizer.add(@notebook, 1, GROW|ALL, 0)
    @p_editor.set_sizer(sizer)
    @p_editor.layout
  end
  
  def init_frame_contentse
    start_up = YAML.load_file(StartUpBean::FILE_PATH)
    self.set_size(Rect.new(start_up.rect[0],start_up.rect[1],start_up.rect[2],start_up.rect[3]))
    @sw_dir.set_sash_position(start_up.sashes[0])
    @sw_editor.set_sash_position(start_up.sashes[1])
    if proj_def = start_up.proj_def then
      init_project_tree(proj_def)
    end
    if files = start_up.files then
      load_recent_files(files)
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
          # put it into the project tree
          item = @dc_proj.append_item(parent, child, 1, 2, child_path)
          parent_list.push(item)
          next
        end
        if FileTest.file?(child_path) && child_path.include?("\.xrc") then
          # put it into the project tree
          @dc_proj.append_item(parent, child, 3, 3, child_path)
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
  
  def file_close_event_handler
    evt_auinotebook_page_close(@notebook) do |evt| 
      # now we just can get the caption of the tab, and we should use it to query the path
      @opened.delete(@notebook.get_page(evt.get_old_selection).get_name)
    end
  end
  
end