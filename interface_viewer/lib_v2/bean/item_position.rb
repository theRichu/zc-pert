class ItemPosition
  def initialize
    @item, @position = Hash.new, Hash.new
  end
  
  def add(xml_path, xpath, item, pos_start, pos_end)
    key = xml_path + "|" + xpath
    @item[key] = item
    @position[key] = [pos_start, pos_end]
  end
  
  def get_item(path_xpath)
    return @item[path_xpath]
  end
  
  def get_pos(path_xpath)
    return @position[path_xpath]
  end
end