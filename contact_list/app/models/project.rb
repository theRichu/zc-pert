class Project < ActiveRecord::Base
  DEFAULT=':public'
  has_many :groups
end
