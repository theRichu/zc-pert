class CreateGrantGgroups < ActiveRecord::Migration
  def self.up
    create_table :grant_ggroups do |t|
      t.integer :grant_id
      t.integer :ggroup_id

      t.timestamps
    end
  end

  def self.down
    drop_table :grant_ggroups
  end
end
