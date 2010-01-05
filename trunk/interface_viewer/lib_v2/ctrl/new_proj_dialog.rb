# This class was automatically generated from XRC source. It is not
# recommended that this file is edited directly; instead, inherit from
# this class and extend its behaviour there.  
#
# Source file: new_proj_dialog.xrc 
# Generated at: 2010-01-03 15:33:17 +0800

class NewPorjDialog < Wx::Dialog
  ID_OK = 0
  ID_CANCEL = 1
  
  attr_reader :msg, :tc_proj_name, :tc_dir, :b_select_dir, :b_ok,
              :b_cancel
  
  def initialize(parent = nil)
    super()
    xml = Wx::XmlResource.get
    xml.flags = 2 # Wx::XRC_NO_SUBCLASSING
    xml.init_all_handlers
    xml.load("./res/xrc/new_proj_dialog.xrc")
    xml.load_dialog_subclass(self, parent, "NewPorjDialog")
    
    finder = lambda do | x | 
      int_id = Wx::xrcid(x)
      begin
        Wx::Window.find_window_by_id(int_id, self) || int_id
        # Temporary hack to work around regression in 1.9.2; remove
        # begin/rescue clause in later versions
      rescue RuntimeError
        int_id
      end
    end
    
    @msg = finder.call("msg")
    @tc_proj_name = finder.call("tc_proj_name")
    @tc_dir = finder.call("tc_dir")
    @b_select_dir = finder.call("b_select_dir")
    @b_ok = finder.call("b_ok")
    @b_cancel = finder.call("b_cancel")
    if self.class.method_defined? "on_init"
      self.on_init()
    end
  end
  
  def on_init
    self.set_icon(Icon.new("./res/icon/new.png"))
    self.evt_for_select_dir()
    self.evt_for_cancel()
    self.evt_for_ok()
  end
  
  def evt_for_select_dir
    evt_button(@b_select_dir) do |evt|
      dir_dialog = Wx::DirDialog.new(self)
      if (Wx::ID_OK == dir_dialog.show_modal) then
        @tc_dir.set_value(dir_dialog.get_path)
      end 
    end
  end
  
  def evt_for_cancel
    evt_button(@b_cancel) do |evt|
      end_modal(ID_CANCEL)
    end
  end
  
  def evt_for_ok
    evt_button(@b_ok) do |evt|
      valid_failed = false
      if @tc_proj_name.is_empty || Dir.entries("./res/yaml/").include?(@tc_proj_name.get_value + ".yaml") then
        @msg.set_label("Please input a unique project name!")
        @msg.set_foreground_colour(RED)
        valid_failed = true
      end
      if @tc_dir.is_empty || !FileTest.directory?(@tc_dir.get_value) then
        @msg.set_label("Please select directory for project!")
        @msg.set_foreground_colour(RED)
        valid_failed = true
      end
      unless valid_failed then
        end_modal(ID_OK)
      end
    end
  end
  
  def get_proj_name
    if @tc_proj_name.is_empty || Dir.entries("./res/yaml/").include?(@tc_proj_name.get_value + ".yaml") then
      return nil
    else
      return @tc_proj_name.get_value
    end
    
  end
  
  def get_proj_dir
    if @tc_dir.is_empty || !FileTest.directory?(@tc_dir.get_value) then
      return nil
    else
      return @tc_dir.get_value
    end
  end
end 
