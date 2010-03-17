class CreateGgroups < ActiveRecord::Migration
  def self.up
    create_table :ggroups do |t|
      t.string :gg_name
      t.string :gg_desc

      t.timestamps
    end
  end

  def self.down
    drop_table :ggroups
  end
end
