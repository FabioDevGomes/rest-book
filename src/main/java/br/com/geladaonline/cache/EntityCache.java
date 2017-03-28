package br.com.geladaonline.cache;

import java.lang.ref.SoftReference;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

public class EntityCache {

	private Map<String, SoftReference<EntityDatePair>> objectCached;
	//remoção de precição em milisegundo devido ao protocolo HTTP não ter esse suporte
	private static final long INTERVALO_CEGO = 1000L;	
	
	public EntityCache() {
		this.objectCached = new ConcurrentHashMap<>();
	}
	
	public void put(String path, Object entity){
//		TimeZone.setDefault(TimeZone.getTimeZone("BRT"));
		EntityDatePair pair = new EntityDatePair(entity, new Date());
		SoftReference<EntityDatePair> sr = new SoftReference<EntityDatePair>(pair);
		this.objectCached.put(path, sr);
	}
	
	public boolean isUpdated(String path, Date since){
		//não é uma lista e é parametrizado
		SoftReference<EntityDatePair> sr = objectCached.get(path);
		if(sr != null){
			
			EntityDatePair pair = sr.get();
			if(pair == null){
				objectCached.remove(path);
				return true;
			}
			
			long tempoAmazenado = pair.getDate().getTime() / INTERVALO_CEGO;
			long tempoFornecido = since.getTime() / INTERVALO_CEGO;
			
			return tempoAmazenado > tempoFornecido;
			
		}
		
		return true;
	}
	
}
