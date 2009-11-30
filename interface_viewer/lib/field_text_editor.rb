require "wx"

class FieldTextEditor < Wx::GridCellTextEditor
  
  attr_writer :tree_fields, :btn_save

  def end_edit(row, col, grid)
    is_changed = super(row, col, grid)
    if is_changed && @tree_fields then
      item_id = grid.get_row_label_value(row).to_i
      @tree_fields.get_item_data(item_id).content = grid.get_cell_value(row, col)
    end
    @btn_save.set_label("* Save Change *")
    return is_changed
  end
end