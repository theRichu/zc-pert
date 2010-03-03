class CreateGrants < ActiveRecord::Migration
  def self.up
    create_table :grants do |t|
      t.string :grant_name
      t.string :grant_contrl
      t.string :grant_action
      t.string :grant_target
      t.text :grant_desc

      t.timestamps
    end
  end

  def self.down
    drop_table :grants
  end
end
