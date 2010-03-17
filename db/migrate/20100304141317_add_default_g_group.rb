class AddDefaultGGroup < ActiveRecord::Migration
  def self.up
    Ggroup.delete_all
    Ggroup.create(:gg_name=>Ggroup::DEFAULT, :gg_desc=>Ggroup::DEFAULT)
  end

  def self.down
    Ggroup.delete_all
  end
end
