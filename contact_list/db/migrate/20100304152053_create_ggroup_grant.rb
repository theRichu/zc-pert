class CreateGgroupGrant < ActiveRecord::Migration
  def self.up
      create_table :ggroups_grants, :id => false do |t|
        t.column :ggroup_id, :integer
        t.column :grant_id, :integer
      end
      add_index :ggroups_grants, [:ggroup_id, :grant_id]
      add_index :ggroups_grants, [:grant_id]
    end
    def self.down
      remove_index :ggroups_grants, [:ggroup_id]
      remove_index :ggroups_grants, [:ggroup_id, :grant_id]
      drop_table :ggroups_grants
    end
end
