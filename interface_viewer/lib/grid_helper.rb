require 'field_text_editor'

class GridHelper
  
  def GridHelper.include?(grid, item_id)
    return false unless grid || item_id
    0.upto(grid.get_number_rows) do |row|
      curt_id = grid.get_row_label_value(row)
      #puts "curt_id=#{curt_id},item_id=#{item_id},curt_id == item_id.to_s=#{curt_id == item_id.to_s}"
      if curt_id && curt_id == item_id.to_s then
        return true 
      end
    end
    return false
  end
  
  def GridHelper.add(grid, item_id, item_elem, tree_fields, btn_save)
    return unless grid || item_elem || item_id
    return if GridHelper.include?(grid, item_id)
    # add it to the grid
    return unless grid.append_rows
    row = grid.get_number_rows == 0 ? 0 : grid.get_number_rows - 1
    # get the type attribute of the xml node
    type = item_elem.attribute("type").to_s
    if type then
      type = type[4, type.length]
    else
      type = ""
    end
    # set tree item id as the row label
    grid.set_row_label_value(row, item_id.to_s)
    # add the name, type, value to the grid
    grid.set_cell_value(row, 0, item_elem.name)
    grid.set_cell_value(row, 1, type.to_s)
    grid.set_cell_value(row, 2, item_elem.content)
    # set row attributes
    grid.set_row_size(row, 20)
    grid.set_read_only(row, 0)
    grid.set_read_only(row, 1)
    # set column attributes
    grid.auto_size_column(0)
    grid.auto_size_column(1)
    grid.auto_size_column(2)
    #puts "After add: #{grid.get_number_rows}"
    # set cell editor
    editor = FieldTextEditor.new()
    editor.tree_fields = tree_fields
    editor.btn_save = btn_save
    grid.set_cell_editor(row, 2, editor)
  end
  
end