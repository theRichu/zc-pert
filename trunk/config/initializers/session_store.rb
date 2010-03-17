# Be sure to restart your server when you modify this file.

# Your secret key for verifying cookie session data integrity.
# If you change this key, all old sessions will become invalid!
# Make sure the secret is at least 30 characters and all random, 
# no regular words or you'll be exposed to dictionary attacks.
ActionController::Base.session = {
  :key         => '_contact_list_session',
  :secret      => '34ef407ac5edefd23100158f10765cef1dfbfedcdb492b7b6a9c32ae54f5bbcf04f3f2594820ed61224affeba5406f102f95a728f26aaebc916817863ceb6a26'
}

# Use the database for sessions instead of the cookie-based default,
# which shouldn't be used to store highly confidential information
# (create the session table with "rake db:sessions:create")
ActionController::Base.session_store = :active_record_store
