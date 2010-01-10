require 'rubygems'
require 'wx'

class MyFrame < Wx::Frame
  def initialize
    super(nil, :title => "Thread example")
    
    set_menu_bar menubar
    
    #    timer = Wx::Timer.new(self, Wx::ID_ANY)
    #    evt_timer(timer.id) {Thread.pass}
    #    timer.start(10)
    Wx::Timer.every(1) { Thread.pass } 
    
    @text = Wx::TextCtrl.new(self, :style => Wx::TE_MULTILINE)
    @tasks = 0
  end
  
  def menubar
    menubar = Wx::MenuBar.new
    file_menu = Wx::Menu.new
    about_item = Wx::MenuItem.new(file_menu, Wx::ID_ANY, "About")
    evt_menu(about_item) { @text.append_text "About\n" }
    file_menu.append_item about_item
    start_item = Wx::MenuItem.new(file_menu, Wx::ID_ANY, "Start")
    evt_menu(start_item) { busy_task} 
    
    file_menu.append_item start_item
    
    menubar.append(file_menu, "File")
    menubar
  end
  
  def busy_task
    # start task in a separate thread
    Thread.new do 
      @tasks += 1
      tasks = @tasks
      10.times do 
        @text.append_text "running #{tasks} ... #{Time.new}\n"
        @text.append_text("^_^\n")
      end
      @text.append_text "Thread #{tasks} done\n"
    end
  end
end

Wx::App.run do 
  MyFrame.new.show
end