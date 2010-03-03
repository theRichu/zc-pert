class Staff < ActiveRecord::Base
  belongs_to :group
  belongs_to :ggroup
  has_many :histories
  # self relationship
  belongs_to :superior, :class=>'Staff', :foreign_key=>'report_to'
  has_many :subordinate
end
