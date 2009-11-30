require 'nokogiri'
require File.dirname(__FILE__) + '/grid_helper'

class XMLParser
  
  def initialize(xml_path)
    @xml_path = xml_path
  end
  
  def parse(tree_fields, grid_fields, btn_save)
    @xml_doc = Nokogiri::XML(File.open(@xml_path))
    root_elem = @xml_doc.root
    parent_item_list, child_elem_list = [], []
    # add root item
    root_item = tree_fields.add_root(root_elem.name)
    # initialize the arrays
    root_elem.children.each do |elem|
      if elem.elem? then
        parent_item_list.push(root_item)
        child_elem_list.push(elem)
      end
    end
    # analisis the xml to form the tree
    until child_elem_list.empty?
      current_elem = child_elem_list.shift
      # add the current elem to the tree
      current_item = tree_fields.append_item(parent_item_list.shift, current_elem.name)
      # look into its child
      has_child = false
      current_elem.children.each do |elem|
        if elem.elem? then
          parent_item_list.push(current_item)
          child_elem_list.push(elem)
          has_child = true
        end
      end
      
      # set the data if has no child
      unless has_child then
        tree_fields.set_item_data(current_item, current_elem) 
        # add it to the grid
        GridHelper.add(grid_fields, current_item, current_elem, tree_fields, btn_save)
      end
    end
  end
  
  def save
    #open the file
    #puts @xml_doc
    xml_file = File.open(@xml_path, "w");
    begin
      # save the new value to the file
      @xml_doc.write_xml_to(xml_file, :encoding => @xml_doc.encoding, :indent => 2)
    rescue Exception
      return false
    ensure
      xml_file.close
    end
    return true
  end
  
end