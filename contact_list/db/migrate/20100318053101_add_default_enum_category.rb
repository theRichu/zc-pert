class AddDefaultEnumCategory < ActiveRecord::Migration
  def self.up
    Clenum.delete_all
    Clenum.create(:enum_text => Clenum::TEAM)
    Clenum.create(:enum_text => Clenum::WAVE)
    Clenum.create(:enum_text => Clenum::PRODUCT)
    Clenum.create(:enum_text => Clenum::BASE)
    Clenum.create(:enum_text => Clenum::DEPARTMENT)
    Clenum.create(:enum_text => Clenum::SITE)
    Clenum.create(:enum_text => Clenum::ROLE2)
    Clenum.create(:enum_text => Clenum::ROLE1)
  end

  def self.down
  end
end
