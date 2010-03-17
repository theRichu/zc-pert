require 'test_helper'

class ClenumsControllerTest < ActionController::TestCase
  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:clenums)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create clenum" do
    assert_difference('Clenum.count') do
      post :create, :clenum => { }
    end

    assert_redirected_to clenum_path(assigns(:clenum))
  end

  test "should show clenum" do
    get :show, :id => clenums(:one).to_param
    assert_response :success
  end

  test "should get edit" do
    get :edit, :id => clenums(:one).to_param
    assert_response :success
  end

  test "should update clenum" do
    put :update, :id => clenums(:one).to_param, :clenum => { }
    assert_redirected_to clenum_path(assigns(:clenum))
  end

  test "should destroy clenum" do
    assert_difference('Clenum.count', -1) do
      delete :destroy, :id => clenums(:one).to_param
    end

    assert_redirected_to clenums_path
  end
end
