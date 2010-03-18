class AddStatusToStaff < ActiveRecord::Migration
  def self.up
    add_column :staffs, :status, :boolean, :default => true
  end

  def self.down
    remove_column :staffs, :status
  end
end
