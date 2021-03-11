import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IReseller, defaultValue } from 'app/shared/model/reseller.model';

export const ACTION_TYPES = {
  FETCH_RESELLER_LIST: 'reseller/FETCH_RESELLER_LIST',
  FETCH_RESELLER: 'reseller/FETCH_RESELLER',
  CREATE_RESELLER: 'reseller/CREATE_RESELLER',
  UPDATE_RESELLER: 'reseller/UPDATE_RESELLER',
  DELETE_RESELLER: 'reseller/DELETE_RESELLER',
  RESET: 'reseller/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IReseller>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ResellerState = Readonly<typeof initialState>;

// Reducer

export default (state: ResellerState = initialState, action): ResellerState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RESELLER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RESELLER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_RESELLER):
    case REQUEST(ACTION_TYPES.UPDATE_RESELLER):
    case REQUEST(ACTION_TYPES.DELETE_RESELLER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_RESELLER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RESELLER):
    case FAILURE(ACTION_TYPES.CREATE_RESELLER):
    case FAILURE(ACTION_TYPES.UPDATE_RESELLER):
    case FAILURE(ACTION_TYPES.DELETE_RESELLER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_RESELLER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_RESELLER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_RESELLER):
    case SUCCESS(ACTION_TYPES.UPDATE_RESELLER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_RESELLER):
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

const apiUrl = 'api/resellers';

// Actions

export const getEntities: ICrudGetAllAction<IReseller> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_RESELLER_LIST,
  payload: axios.get<IReseller>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IReseller> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RESELLER,
    payload: axios.get<IReseller>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IReseller> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RESELLER,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IReseller> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RESELLER,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IReseller> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RESELLER,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
