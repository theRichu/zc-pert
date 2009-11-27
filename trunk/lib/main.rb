require "wx"
require File.dirname(__FILE__) + '/InterfaceViewerFrame'

class MyApp < Wx::App
  def on_init()
    frame = InterfaceViewerFrame.new()
    frame.show()
  end
end

if __FILE__ == $0
  MyApp.new.main_loop
end