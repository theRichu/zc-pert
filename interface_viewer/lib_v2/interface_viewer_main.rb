require "wx"
require 'interface_viewer_frame'

class MyApp < Wx::App
  def on_init()
    frame = InterfaceViewerFrame.new()
    #    a = Thread.new {frame.init_frame_contents()}
    frame.show()
    #    if frame.is_shown_on_screen then
    #      a.join
    #    end
  end
end

if __FILE__ == $0
  MyApp.new.main_loop
end