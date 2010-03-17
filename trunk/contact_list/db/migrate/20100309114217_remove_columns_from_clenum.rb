class RemoveColumnsFromClenum < ActiveRecord::Migration
  def self.up
    # for clenums table
    remove_column(:clenums, :enum_code)
    rename_column(:clenums, :parent, :category_id)
    # for staffs table
    rename_column(:staffs, :wave, :wave_id)
    rename_column(:staffs, :team, :team_id)
    rename_column(:staffs, :role1, :role1_id)
    rename_column(:staffs, :role2, :role2_id)
    rename_column(:staffs, :site, :site_id)
    rename_column(:staffs, :dept, :dept_id)
    rename_column(:staffs, :base, :base_id)
    rename_column(:staffs, :product, :product_id)
  end

  def self.down
  end
end
