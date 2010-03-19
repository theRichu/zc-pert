class AddDefaultGGroup < ActiveRecord::Migration
  def self.up
    Ggroup.delete_all
    Ggroup.create(:gg_name=>Ggroup::DEFAULT, :gg_desc=>Ggroup::DEFAULT)
    Ggroup.create(:gg_name=>Ggroup::STAFF, :gg_desc=>Ggroup::STAFF)
    Ggroup.create(:gg_name=>Ggroup::PMO, :gg_desc=>Ggroup::PMO)
  end

  def self.down
    Ggroup.delete_all
  end
end
