class XMLParser
  
  def initialize(xml_path, tree_fields)
    @xml_path = xml_path
    @tree_fields = tree_fields
    @item_position = Hash.new
  end
  
  def pre_parse
    xml_doc = Nokogiri::XML(File.open(@xml_path))
    root_elem = xml_doc.root
    @xml_content = xml_doc.to_s
    parent_item_list, child_elem_list = [], []
    # add root item
    root_item = @tree_fields.add_root(root_elem.name, 0, 0)
    @tree_fields.set_item_data(root_item, root_elem)
    # add the postion for root element
    pos_in_parent = @xml_content.index(root_elem.to_s)
    pos_start = pos_in_parent + count_line_end(@xml_content[0, pos_in_parent]) + 1 # the 1 for the < of the tag
    pos_end = pos_start + root_elem.name.length
    @item_position[root_item] = [pos_start, pos_end]
    # initialize the arrays
    root_elem.children.each do |elem|
      if elem.elem? then
        parent_item_list.push(root_item)
        child_elem_list.push(elem)
      end
    end
    return parent_item_list, child_elem_list
  end
  
  def parse
    condition_list = pre_parse()
    parent_item_list = condition_list[0]
    child_elem_list =  condition_list[1]
    form_tree(parent_item_list, child_elem_list)
    return @item_position
  end
  
  def form_tree(parent_item_list, child_elem_list)
    # analisis the xml to form the tree
    until child_elem_list.empty?
      parent_item = parent_item_list.shift
      current_elem = child_elem_list.shift
      # add the current elem to the tree
      current_item = @tree_fields.append_item(parent_item, current_elem.name, 1, 1)
      # look into its child
      has_child = false
      current_elem.children.each do |elem|
        if elem.elem? then
          parent_item_list.push(current_item)
          child_elem_list.push(elem)
          has_child = true
        end
      end
      set_item_data(parent_item, current_item, current_elem) 
    end
  end
  
  def set_item_data(parent_item, current_item, current_elem)
    parent_pos = @item_position[parent_item]
    parent_content = @tree_fields.get_item_data(parent_item)
    if parent_pos && parent_content then
      parent_start = parent_pos[0]
      parent_white = parent_pos[2]
      
      parent_content = parent_content.to_s
      pos_in_parent = parent_content.index(current_elem.to_s)
      other_content = parent_content[0, pos_in_parent]
      
      child_start = parent_start + pos_in_parent + count_line_end(other_content)
      child_end = child_start + current_elem.name.length
      @item_position[current_item] = [child_start, child_end]
    end
    @tree_fields.set_item_data(current_item, current_elem)
  end
  
  def count_line_end(content)
    return 0 unless content
    return 0 if content.length == 0
    return content.count("\n")
  end
  
end