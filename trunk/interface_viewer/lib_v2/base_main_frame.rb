# This class was automatically generated from XRC source. It is not
# recommended that this file is edited directly; instead, inherit from
# this class and extend its behaviour there.  
#
# Source file: Interface_viewer_v2.xrc 
# Generated at: 2009-12-31 18:42:01 +0800

class BasicMainFrame < Wx::Frame
	
	attr_reader :mi_newproj, :mi_openproj, :mi_save, :mi_exit, :mi_undo,
              :mi_redo, :mi_copy, :mi_cut, :mi_paste, :mi_find,
              :mi_about, :t_newproj, :t_open, :t_save, :t_undo,
              :t_redo, :t_cut, :t_copy, :t_paste, :t_find, :sw_dir, :dc_proj,
              :tc_key, :b_search, :sw_editor, :p_editor,
              :tc_outline, :statusbar
	
	def initialize(parent = nil)
		super()
		xml = Wx::XmlResource.get
		xml.flags = 2 # Wx::XRC_NO_SUBCLASSING
		xml.init_all_handlers
		xml.load("./res/xrc/interface_viewer_v2.xrc")
		xml.load_frame_subclass(self, parent, "MainFrame")

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
		
		@mi_newproj = finder.call("mi_newproj")
		@mi_openproj = finder.call("mi_openproj")
		@mi_save = finder.call("mi_save")
		@mi_exit = finder.call("mi_exit")
		@mi_undo = finder.call("mi_undo")
		@mi_redo = finder.call("mi_redo")
		@mi_copy = finder.call("mi_copy")
		@mi_cut = finder.call("mi_cut")
		@mi_paste = finder.call("mi_paste")
		@mi_find = finder.call("mi_find")
		@mi_about = finder.call("mi_about")
		@t_newproj = finder.call("t_newproj")
		@t_open = finder.call("t_open")
		@t_save = finder.call("t_save")
		@t_undo = finder.call("t_undo")
		@t_redo = finder.call("t_redo")
		@t_cut = finder.call("t_cut")
		@t_copy = finder.call("t_copy")
		@t_paste = finder.call("t_paste")
    @t_find = finder.call("t_find")
		@sw_dir = finder.call("sw_dir")
		@dc_proj = finder.call("dc_proj")
		@tc_key = finder.call("tc_key")
		@b_search = finder.call("b_search")
		@sw_editor = finder.call("sw_editor")
		@p_editor = finder.call("p_editor")
		@tc_outline = finder.call("tc_outline")
		@statusbar = finder.call("statusBar")
		if self.class.method_defined? "on_init"
			self.on_init()
		end
	end
end


