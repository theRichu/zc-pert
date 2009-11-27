require "wx"
# This class was automatically generated from XRC source. It is not
# recommended that this file is edited directly; instead, inherit from
# this class and extend its behaviour there.  
#
# Source file: interface_viewer.xrc 
# Generated at: Thu Nov 26 17:29:36 +0800 2009

class BaseMainFrame < Wx::Frame
  
  attr_reader :text_inter_id, :radio_rs, :radio_rq, :text_dir,
              :btn_select_dir, :btn_search, :tree_file,
              :static_text_field_name, :text_field_value, :btn_save,
              :status_bar
  
  def initialize(parent = nil)
    super()
    xml = Wx::XmlResource.get
    xml.flags = 2 # Wx::XRC_NO_SUBCLASSING
    xml.init_all_handlers
    xml.load(File.dirname(__FILE__) + "/interface_viewer.xrc")
    xml.load_frame_subclass(self, parent, "main_frame")
    
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
    
    @text_inter_id = finder.call("text_inter_id")
    @radio_rs = finder.call("radio_rs")
    @radio_rq = finder.call("radio_rq")
    @text_dir = finder.call("text_dir")
    @btn_select_dir = finder.call("btn_select_dir")
    @btn_search = finder.call("btn_search")
    @tree_file = finder.call("tree_file")
    @static_text_field_name = finder.call("static_text_field_name")
    @text_field_value = finder.call("text_field_value")
    @btn_save = finder.call("btn_save")
    @status_bar = finder.call("status_bar")
    if self.class.method_defined? "on_init"
      self.on_init()
    end
  end
end


