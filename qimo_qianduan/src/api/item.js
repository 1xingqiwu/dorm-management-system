import request from '../utils/request';

export const getItemListApi = () => request.get('/item/list');
export const addItemApi = (data) => request.post('/item/add', data);
export const updateItemApi = (data) => request.post('/item/update', data);
export const deleteItemApi = (id) => request.post(`/item/delete/${id}`);

export const borrowItemApi = (itemId) => request.post(`/item/borrow/${itemId}`);
export const returnItemApi = (recordId, itemId) => request.post('/item/return', null, { params: { recordId, itemId } });
export const getRecordListApi = () => request.get('/item/record/list');