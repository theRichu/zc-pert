require 'test_helper'

class MappingsControllerTest < ActionController::TestCase
  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:mappings)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create mapping" do
    assert_difference('Mapping.count') do
      post :create, :mapping => { }
    end

    assert_redirected_to mapping_path(assigns(:mapping))
  end

  test "should show mapping" do
    get :show, :id => mappings(:one).to_param
    assert_response :success
  end

  test "should get edit" do
    get :edit, :id => mappings(:one).to_param
    assert_response :success
  end

  test "should update mapping" do
    put :update, :id => mappings(:one).to_param, :mapping => { }
    assert_redirected_to mapping_path(assigns(:mapping))
  end

  test "should destroy mapping" do
    assert_difference('Mapping.count', -1) do
      delete :destroy, :id => mappings(:one).to_param
    end

    assert_redirected_to mappings_path
  end
end
