import request from '@/utils/request'

const api_name = '/admin/core/dict'

export default {
  listByParentId(parentId) {
    return request({
      url: `${api_name}/listByParentId/${parentId}`,
      method: 'get'
    })
  }
}
