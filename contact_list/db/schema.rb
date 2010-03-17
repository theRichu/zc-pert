# This file is auto-generated from the current state of the database. Instead of editing this file, 
# please use the migrations feature of Active Record to incrementally modify your database, and
# then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your database schema. If you need
# to create the application database on another system, you should be using db:schema:load, not running
# all the migrations from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended to check this file into your version control system.

ActiveRecord::Schema.define(:version => 20100317021957) do

  create_table "clenums", :force => true do |t|
    t.integer  "category_id"
    t.string   "enum_text"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "ggroups", :force => true do |t|
    t.string   "gg_name"
    t.string   "gg_desc"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "ggroups_grants", :id => false, :force => true do |t|
    t.integer "ggroup_id"
    t.integer "grant_id"
  end

  add_index "ggroups_grants", ["ggroup_id", "grant_id"], :name => "index_ggroups_grants_on_ggroup_id_and_grant_id"
  add_index "ggroups_grants", ["grant_id"], :name => "index_ggroups_grants_on_grant_id"

  create_table "grants", :force => true do |t|
    t.string   "grant_name"
    t.string   "grant_contrl"
    t.string   "grant_action"
    t.string   "grant_target"
    t.text     "grant_desc"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "groups", :force => true do |t|
    t.integer  "project_id"
    t.string   "group_name"
    t.text     "group_desc"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "histories", :force => true do |t|
    t.integer  "staff_id"
    t.string   "action"
    t.string   "target"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "mappings", :force => true do |t|
    t.string   "xls_column"
    t.string   "staff_attr"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "projects", :force => true do |t|
    t.string   "project_name"
    t.text     "project_desc"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "sessions", :force => true do |t|
    t.string   "session_id", :null => false
    t.text     "data"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  add_index "sessions", ["session_id"], :name => "index_sessions_on_session_id"
  add_index "sessions", ["updated_at"], :name => "index_sessions_on_updated_at"

  create_table "staffs", :force => true do |t|
    t.integer  "group_id"
    t.integer  "ggroup_id"
    t.string   "en_firstname"
    t.string   "en_lastname"
    t.string   "cn_firstname"
    t.string   "cn_lastname"
    t.integer  "report_to"
    t.integer  "wave_id"
    t.integer  "team_id"
    t.integer  "role1_id"
    t.integer  "role2_id"
    t.integer  "site_id"
    t.integer  "dept_id"
    t.integer  "base_id"
    t.string   "photo_file_name"
    t.integer  "product_id"
    t.string   "base_mobile"
    t.string   "current_mobile"
    t.string   "onsite_mobile"
    t.string   "office_phone"
    t.string   "longtop_email"
    t.string   "s1_eamil"
    t.string   "svn_account"
    t.string   "msn"
    t.string   "skype"
    t.string   "passport"
    t.date     "dob"
    t.string   "username"
    t.string   "password"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string   "photo_content_type"
    t.integer  "photo_file_size"
    t.datetime "photo_updated_at"
  end

end
