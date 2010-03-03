class CreateMappings < ActiveRecord::Migration
  def self.up
    create_table :mappings do |t|
      t.string :xls_column
      t.string :staff_attr

      t.timestamps
    end
  end

  def self.down
    drop_table :mappings
  end
end
