class CreateClenums < ActiveRecord::Migration
  def self.up
    create_table :clenums do |t|
      t.string :enum_code
      t.string :parent
      t.string :enum_text

      t.timestamps
    end
  end

  def self.down
    drop_table :clenums
  end
end
