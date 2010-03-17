class Staff < ActiveRecord::Base
  belongs_to :group
  belongs_to :ggroup
  has_many :histories

  # self relationship
  belongs_to :superior, :class_name=>'Staff', :foreign_key=>'report_to'
  has_many :subordinates, :class_name=>'Staff', :foreign_key=>'report_to'

  # relationship with clenum table
  belongs_to :wave, :class_name=>'Clenum', :foreign_key=>'wave_id'
  belongs_to :team, :class_name=>'Clenum', :foreign_key=>'team_id'
  belongs_to :role1, :class_name=>'Clenum', :foreign_key=>'role1_id'
  belongs_to :role2, :class_name=>'Clenum', :foreign_key=>'role2_id'
  belongs_to :site, :class_name=>'Clenum', :foreign_key=>'site_id'
  belongs_to :dept, :class_name=>'Clenum', :foreign_key=>'dept_id'
  belongs_to :base, :class_name=>'Clenum', :foreign_key=>'base_id'
  belongs_to :product, :class_name=>'Clenum', :foreign_key=>'product_id'

  # virtual properties
  def english_name
    en_firstname + '&nbsp;' + en_lastname
  end

  def chinese_name
    cn_lastname + cn_firstname
  end

end
