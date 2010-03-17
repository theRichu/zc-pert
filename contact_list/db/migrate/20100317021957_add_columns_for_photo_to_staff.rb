class AddColumnsForPhotoToStaff < ActiveRecord::Migration
  def self.up
    rename_column :staffs, :photo, :photo_file_name
    add_column :staffs, :photo_content_type, :string
    add_column :staffs, :photo_file_size, :integer
    add_column :staffs, :photo_updated_at, :datetime
  end

  def self.down
    rename_column :staffs, :photo_file_name, :photo
    remove_column :staffs, :photo_content_type
    remove_column :staffs, :photo_file_size
    remove_column :staffs, :photo_updated_at
  end
end
