module ProjectsHelper

  def order_by_menu
    menu_items = Array.new
    items = {:project_name=>'Project Name', :project_desc=>'Project Description'}

    items.each do |key,value|
      if @order_by == key.to_s then
        menu_items << value
      else
        menu_items << link_to_remote(value, :url=>{:action=>'index', :order_by=>key.to_s}, :method=>:get, :update=>'board_content')
      end
    end

    menu_items
  end
end