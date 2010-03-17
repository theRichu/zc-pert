class ClenumsController < ApplicationController
  layout 'board_content'
 
  # GET /clenums
  # GET /clenums.xml
  def index
    @order_by = params[:order_by] || 'category.enum_text'
    @page = params[:page]
    @clenums = Clenum.paginate :select=>'enum.*', :joins=>'as enum inner join clenums as category on enum.category_id = category.id', :conditions=>'enum.category_id is not null', :page=>@page, :per_page=>12, :order=>@order_by

    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @clenums }
    end
  end

  # GET /clenums/1
  # GET /clenums/1.xml
  def show
    @clenum = Clenum.find(params[:ids])

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @clenum }
    end
  end

  # GET /clenums/new
  # GET /clenums/new.xml
  def new
    @clenum = Clenum.new

    respond_to do |format|
      format.html # new.html.erb
      format.xml  { render :xml => @clenum }
    end
  end

  # GET /clenums/1/edit
  def edit
    @clenum = Clenum.find(params[:ids])
  end

  # POST /clenums
  # POST /clenums.xml
  def create
    @clenum = Clenum.new(params[:clenum])

    respond_to do |format|
      if @clenum.save
        flash[:notice] = 'Clenum was successfully created.'
        format.html { redirect_to(:action=>'show', :ids=>@clenum) }
        format.xml  { render :xml => @clenum, :status => :created, :location => @clenum }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @clenum.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /clenums/1
  # PUT /clenums/1.xml
  def update
    @clenum = Clenum.find(params[:ids])

    respond_to do |format|
      if @clenum.update_attributes(params[:clenum])
        flash[:notice] = 'Clenum was successfully updated.'
        format.html { redirect_to(:action=>'show', :ids=>@clenum) }
        format.xml  { head :ok }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @clenum.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /clenums/1
  # DELETE /clenums/1.xml
  def destroy
    Clenum.destroy params[:ids].split(',')

    respond_to do |format|
      format.html { redirect_to(clenums_url) }
      format.xml  { head :ok }
    end
  end
end
