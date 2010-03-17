class CreateHistories < ActiveRecord::Migration
  def self.up
    create_table :histories do |t|
      t.integer :staff_id
      t.string :action
      t.string :target

      t.timestamps
    end
  end

  def self.down
    drop_table :histories
  end
end
