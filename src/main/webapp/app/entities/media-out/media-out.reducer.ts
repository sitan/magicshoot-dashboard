import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMediaOut, defaultValue } from 'app/shared/model/media-out.model';

export const ACTION_TYPES = {
  FETCH_MEDIAOUT_LIST: 'mediaOut/FETCH_MEDIAOUT_LIST',
  FETCH_MEDIAOUT: 'mediaOut/FETCH_MEDIAOUT',
  CREATE_MEDIAOUT: 'mediaOut/CREATE_MEDIAOUT',
  UPDATE_MEDIAOUT: 'mediaOut/UPDATE_MEDIAOUT',
  DELETE_MEDIAOUT: 'mediaOut/DELETE_MEDIAOUT',
  RESET: 'mediaOut/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMediaOut>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type MediaOutState = Readonly<typeof initialState>;

// Reducer

export default (state: MediaOutState = initialState, action): MediaOutState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MEDIAOUT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MEDIAOUT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_MEDIAOUT):
    case REQUEST(ACTION_TYPES.UPDATE_MEDIAOUT):
    case REQUEST(ACTION_TYPES.DELETE_MEDIAOUT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_MEDIAOUT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MEDIAOUT):
    case FAILURE(ACTION_TYPES.CREATE_MEDIAOUT):
    case FAILURE(ACTION_TYPES.UPDATE_MEDIAOUT):
    case FAILURE(ACTION_TYPES.DELETE_MEDIAOUT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MEDIAOUT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MEDIAOUT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_MEDIAOUT):
    case SUCCESS(ACTION_TYPES.UPDATE_MEDIAOUT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_MEDIAOUT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/media-outs';

// Actions

export const getEntities: ICrudGetAllAction<IMediaOut> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_MEDIAOUT_LIST,
  payload: axios.get<IMediaOut>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IMediaOut> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MEDIAOUT,
    payload: axios.get<IMediaOut>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IMediaOut> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MEDIAOUT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMediaOut> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MEDIAOUT,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMediaOut> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MEDIAOUT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
