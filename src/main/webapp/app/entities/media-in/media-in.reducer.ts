import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMediaIn, defaultValue } from 'app/shared/model/media-in.model';

export const ACTION_TYPES = {
  FETCH_MEDIAIN_LIST: 'mediaIn/FETCH_MEDIAIN_LIST',
  FETCH_MEDIAIN: 'mediaIn/FETCH_MEDIAIN',
  CREATE_MEDIAIN: 'mediaIn/CREATE_MEDIAIN',
  UPDATE_MEDIAIN: 'mediaIn/UPDATE_MEDIAIN',
  DELETE_MEDIAIN: 'mediaIn/DELETE_MEDIAIN',
  RESET: 'mediaIn/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMediaIn>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type MediaInState = Readonly<typeof initialState>;

// Reducer

export default (state: MediaInState = initialState, action): MediaInState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MEDIAIN_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MEDIAIN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_MEDIAIN):
    case REQUEST(ACTION_TYPES.UPDATE_MEDIAIN):
    case REQUEST(ACTION_TYPES.DELETE_MEDIAIN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_MEDIAIN_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MEDIAIN):
    case FAILURE(ACTION_TYPES.CREATE_MEDIAIN):
    case FAILURE(ACTION_TYPES.UPDATE_MEDIAIN):
    case FAILURE(ACTION_TYPES.DELETE_MEDIAIN):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MEDIAIN_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MEDIAIN):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_MEDIAIN):
    case SUCCESS(ACTION_TYPES.UPDATE_MEDIAIN):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_MEDIAIN):
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

const apiUrl = 'api/media-ins';

// Actions

export const getEntities: ICrudGetAllAction<IMediaIn> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_MEDIAIN_LIST,
  payload: axios.get<IMediaIn>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IMediaIn> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MEDIAIN,
    payload: axios.get<IMediaIn>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IMediaIn> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MEDIAIN,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMediaIn> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MEDIAIN,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMediaIn> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MEDIAIN,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
