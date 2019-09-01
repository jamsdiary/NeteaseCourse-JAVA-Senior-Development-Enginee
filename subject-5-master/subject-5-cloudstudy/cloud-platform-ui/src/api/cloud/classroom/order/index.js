import fetch from 'utils/fetch';

export function page(query) {
  return fetch({
    url: '/order/cloud/classroom/order/page/list',
    method: 'get',
    params: query
  });
}

export function addObj(obj) {
  return fetch({
    url: '/api/cloud.classroom.order/order',
    method: 'post',
    data: obj
  });
}

export function getObj(id) {
  return fetch({
    url: '/order/cloud/classroom/order/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return fetch({
    url: '/order/cloud/classroom/order/' + id,
    method: 'delete'
  })
}

export function putObj(id, obj) {
  return fetch({
    url: '/order/cloud/classroom/order',
    method: 'put',
    data: obj
  })
}
