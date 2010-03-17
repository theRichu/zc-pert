class MappingsController < ApplicationController
  # GET /mappings
  # GET /mappings.xml
  def index
    @mappings = Mapping.all

    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @mappings }
    end
  end

  # GET /mappings/1
  # GET /mappings/1.xml
  def show
    @mapping = Mapping.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @mapping }
    end
  end

  # GET /mappings/new
  # GET /mappings/new.xml
  def new
    @mapping = Mapping.new

    respond_to do |format|
      format.html # new.html.erb
      format.xml  { render :xml => @mapping }
    end
  end

  # GET /mappings/1/edit
  def edit
    @mapping = Mapping.find(params[:id])
  end

  # POST /mappings
  # POST /mappings.xml
  def create
    @mapping = Mapping.new(params[:mapping])

    respond_to do |format|
      if @mapping.save
        flash[:notice] = 'Mapping was successfully created.'
        format.html { redirect_to(@mapping) }
        format.xml  { render :xml => @mapping, :status => :created, :location => @mapping }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @mapping.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /mappings/1
  # PUT /mappings/1.xml
  def update
    @mapping = Mapping.find(params[:id])

    respond_to do |format|
      if @mapping.update_attributes(params[:mapping])
        flash[:notice] = 'Mapping was successfully updated.'
        format.html { redirect_to(@mapping) }
        format.xml  { head :ok }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @mapping.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /mappings/1
  # DELETE /mappings/1.xml
  def destroy
    @mapping = Mapping.find(params[:id])
    @mapping.destroy

    respond_to do |format|
      format.html { redirect_to(mappings_url) }
      format.xml  { head :ok }
    end
  end
end
