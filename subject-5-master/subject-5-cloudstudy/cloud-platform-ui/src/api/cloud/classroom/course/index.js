import fetch from 'utils/fetch';

export function page(query) {
  return fetch({
    url: '/course/cloud/classroom/course/page/list',
    method: 'get',
    params: query
  });
}

export function addObj(obj) {
  return fetch({
    url: '/course/cloud/classroom/course',
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    data: obj
  });
}

export function getObj(id) {
  return fetch({
    url: '/course/cloud/classroom/course/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return fetch({
    url: '/course/cloud/classroom/course/' + id,
    method: 'delete'
  })
}

export function putObj(id, obj) {
  return fetch({
    url: '/course/cloud/classroom/course/' + id,
    method: 'post',
    data: obj
  })
}


export function putPicture(id, obj) {
  return fetch({
    url: '/course/cloud/classroom/course/' + id,
    method: 'put',
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    data: obj
  })
}
