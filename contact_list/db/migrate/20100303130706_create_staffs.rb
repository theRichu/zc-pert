class CreateStaffs < ActiveRecord::Migration
  def self.up
    create_table :staffs do |t|
      t.integer :group_id
      t.integer :ggroup_id
      t.string :en_firstname
      t.string :en_lastname
      t.string :cn_firstname
      t.string :cn_lastname
      t.integer :report_to
      t.string :wave
      t.string :team
      t.string :role1
      t.string :role2
      t.string :site
      t.string :dept
      t.string :base
      t.string :photo
      t.string :product
      t.string :base_mobile
      t.string :current_mobile
      t.string :onsite_mobile
      t.string :office_phone
      t.sting :longtop_email
      t.string :s1_eamil
      t.string :svn_account
      t.string :msn
      t.string :skype
      t.string :passport
      t.date :dob
      t.string :username
      t.string :password

      t.timestamps
    end
  end

  def self.down
    drop_table :staffs
  end
end
