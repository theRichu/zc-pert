
# This class was automatically generated from XRC source. It is not
# recommended that this file is edited directly; instead, inherit from
# this class and extend its behaviour there.  
#
# Source file: interface_viewer.xrc 
# Generated at: Fri Nov 27 22:47:03 +0800 2009

class BaseMainFrame < Wx::Frame
	
	attr_reader :text_inter_id, :radio_rs, :radio_rq, :text_look_in,
              :btn_select_dir, :btn_search, :text_file_path,
              :btn_select_file, :btn_view_file, :listbox_history,
              :tree_fields, :grid_fields, :btn_save, :status_bar
	
	def initialize(parent = nil)
		super()
		xml = Wx::XmlResource.get
		xml.flags = 2 # Wx::XRC_NO_SUBCLASSING
		xml.init_all_handlers
		#xml.load(File.dirname(__FILE__) + "/interface_viewer.xrc")
                xml.load("interface_viewer.xrc")
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
		@text_look_in = finder.call("text_look_in")
		@btn_select_dir = finder.call("btn_select_dir")
		@btn_search = finder.call("btn_search")
		@text_file_path = finder.call("text_file_path")
		@btn_select_file = finder.call("btn_select_file")
		@btn_view_file = finder.call("btn_view_file")
		@listbox_history = finder.call("listBox_history")
		@tree_fields = finder.call("tree_fields")
		@grid_fields = finder.call("grid_fields")
		@btn_save = finder.call("btn_save")
		@status_bar = finder.call("status_bar")
		if self.class.method_defined? "on_init"
			self.on_init()
		end
	end
end


