import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPrint, defaultValue } from 'app/shared/model/print.model';

export const ACTION_TYPES = {
  FETCH_PRINT_LIST: 'print/FETCH_PRINT_LIST',
  FETCH_PRINT: 'print/FETCH_PRINT',
  CREATE_PRINT: 'print/CREATE_PRINT',
  UPDATE_PRINT: 'print/UPDATE_PRINT',
  DELETE_PRINT: 'print/DELETE_PRINT',
  RESET: 'print/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPrint>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type PrintState = Readonly<typeof initialState>;

// Reducer

export default (state: PrintState = initialState, action): PrintState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PRINT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PRINT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PRINT):
    case REQUEST(ACTION_TYPES.UPDATE_PRINT):
    case REQUEST(ACTION_TYPES.DELETE_PRINT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PRINT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PRINT):
    case FAILURE(ACTION_TYPES.CREATE_PRINT):
    case FAILURE(ACTION_TYPES.UPDATE_PRINT):
    case FAILURE(ACTION_TYPES.DELETE_PRINT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRINT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRINT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PRINT):
    case SUCCESS(ACTION_TYPES.UPDATE_PRINT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PRINT):
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

const apiUrl = 'api/prints';

// Actions

export const getEntities: ICrudGetAllAction<IPrint> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PRINT_LIST,
  payload: axios.get<IPrint>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IPrint> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PRINT,
    payload: axios.get<IPrint>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPrint> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PRINT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPrint> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PRINT,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPrint> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PRINT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
