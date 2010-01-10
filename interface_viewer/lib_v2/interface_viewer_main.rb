require 'rubygems'
require "logger"
require "wx"
require 'interface_viewer_frame'

class MyApp < Wx::App
  def on_init()
    Wx::Timer.every(10) { Thread.pass }
    frame = InterfaceViewerFrame.new()
    frame.init_frame_contents
    frame.show()
  end
end

if __FILE__ == $0
  MyApp.new.main_loop
end