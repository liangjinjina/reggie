function loginApi(data) {
  return $axios({
    'url': '/employee/login',
    'method': 'post',
    data//传给后台的数据
  })//返回后台给的数据
}

function logoutApi(){
  return $axios({
    'url': '/employee/logout',
    'method': 'post',
  })
}
