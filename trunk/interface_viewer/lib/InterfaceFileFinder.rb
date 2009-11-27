# ecoding: utf-8
require 'find'

class InterfaceFileFinder
  # attributes accesssor
  attr_accessor :base_dir
  # consistants
  RQ = "Req\.xml"
  RS = "Resp\.xml"
  
  def initialize(base_dir, status_bar)
    @base_dir = base_dir
    @status_bar = status_bar
  end
  
  # try to find the interface file which match the interfase parameter
  def find(interface, rs_or_rq = RS)
    # if the interface is not specified, return false
    if !interface then
      return nil
    end
    # check the existence of the base_dir
    if !FileTest.directory?(base_dir) then
      return nil
    end
    # scan the response file in the directory
    dir_list = [base_dir]
    until dir_list.empty?
      # get the dir from the list
      current_dir = dir_list.shift
      current_dir << "/" unless current_dir[-1] == "/"
      # begin to traversa this dir, if find, reuslt will be the path, otherwise nil
      result = Dir.foreach(current_dir) do |child|
        # exclude the ., .., svn, web-inf
        next if child == "." || child == ".." || child.include?("\.svn") || child.include?("WEB-INF")
        # form the path
        @status_bar.set_status_text(child_path = current_dir + child)
        
        if FileTest.directory?(child_path) then
          dir_list.push(child_path)
          next
        end
        
        if FileTest.file?(child_path) && child_path.downcase.include?(interface.downcase) && child_path.include?(rs_or_rq) then
          break child_path
        else          next
        end
      end
      # if find break the loop
      break result if result
    end
    #    Find.find(@base_dir) do |path|
    #      # puts "Current: " + path
    #      @status_bar.set_status_text(path)
    #      # skip it the svn and web-inf
    #      if path.include?("\.svn") || path.include?("WEB-INF") then
    #        Find.prune
    #      end
    #      # validate the current path
    #      if FileTest.file?(path) && path.downcase.include?(interface.downcase) && path.include?(rs_or_rq) then
    #        break path
    #      end
    #    end
  end
  
end