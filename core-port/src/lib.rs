#![allow(non_snake_case)]

use std::thread::JoinHandle;
use jni::JNIEnv;
use jni::objects::{GlobalRef, JClass, JMethodID, JObject};
use jni::sys::{_jobject, jint, jlong, jobject, jshort};
use crate::object::FuturePromise;

pub mod object;

#[no_mangle]
pub extern "system" fn Java_org_atomic_commons_futures_OSFuturePromiseImpl_d(env: JNIEnv, _: JObject, handle: jlong, runnable: JObject) {
    let run_method_id: JMethodID = env.get_method_id("org/atomic/commons/futures/PrimitiveRunnable", "run", "()Ljava/lang/Object;").unwrap();
    object::FuturePromise::execute_d(env, handle, runnable, run_method_id)
}

#[no_mangle]
pub extern "system" fn Java_org_atomic_commons_futures_OSFuturePromiseImpl_e(env: JNIEnv, _: JObject, handle: jlong, d: jint, runnable: JObject) {
    let run_method_id: JMethodID = env.get_method_id("org/atomic/commons/futures/PrimitiveRunnable", "run", "()Ljava/lang/Object;").unwrap();
    object::FuturePromise::execute_e(env, handle, runnable, run_method_id, d)
}

#[no_mangle]
pub extern "system" fn Java_org_atomic_commons_futures_OSFuturePromiseImpl_s(_: JNIEnv, _: JObject) {
    FuturePromise::new_storage()
}

#[no_mangle]
pub extern "system" fn Java_org_atomic_commons_futures_OSFuturePromiseImpl_c(_: JNIEnv, _: JObject, handle: jlong) -> jshort {
    let re = object::FuturePromise::of(handle).unwrap();
    if re.thread_handle.as_ref().unwrap().is_finished() { 0 } else { 1 }
}

#[no_mangle]
pub extern "system" fn Java_org_atomic_commons_futures_OSFuturePromiseImpl_j(_: JNIEnv, _: JObject, handle: jlong) -> jobject {
    let handle: Option<JoinHandle<&'_ mut _jobject>> = std::mem::replace(&mut FuturePromise::of(handle).unwrap().thread_handle, None);
    let binding = handle.unwrap().join().unwrap();
    jobject::from(binding)
}