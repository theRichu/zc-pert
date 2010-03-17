class ChangColumnType < ActiveRecord::Migration
  def self.up
    change_column :clenums, :category_id, :integer
    # for staffs table
    change_column :staffs, :wave_id, :integer
    change_column :staffs, :team_id, :integer
    change_column :staffs, :role1_id, :integer
    change_column :staffs, :role2_id, :integer
    change_column :staffs, :site_id, :integer
    change_column :staffs, :dept_id, :integer
    change_column :staffs, :base_id, :integer
    change_column :staffs, :product_id, :integer
  end

  def self.down
  end
end
