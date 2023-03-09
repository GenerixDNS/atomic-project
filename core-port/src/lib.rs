#![allow(non_snake_case)]

use std::thread;
use std::thread::JoinHandle;
use jni::JNIEnv;
use jni::objects::{JMethodID, JObject};
use jni::signature::{Primitive, ReturnType};
use jni::sys::{_jobject, jint, jlong, jobject, jshort};
use crate::object::FuturePromise;

pub mod object;

#[no_mangle]
pub extern "system" fn Java_org_atomic_commons_futures_OSFuturePromiseImpl_d(env: JNIEnv, _: JObject, handle: jlong, runnable: JObject) {
    let run_method_id: JMethodID = env.get_method_id("org/atomic/commons/futures/IPrimitiveRunnable", "run", "()Ljava/lang/Object;").unwrap();
    object::FuturePromise::execute_d(env, handle, runnable, run_method_id)
}

#[no_mangle]
pub extern "system" fn Java_org_atomic_commons_futures_OSFuturePromiseImpl_e(env: JNIEnv, _: JObject, handle: jlong, d: jint, runnable: JObject) {
    let run_method_id: JMethodID = env.get_method_id("org/atomic/commons/futures/IPrimitiveRunnable", "run", "()Ljava/lang/Object;").unwrap();
    object::FuturePromise::execute_e(env, handle, runnable, run_method_id, d)
}

#[no_mangle]
pub extern "system" fn Java_org_atomic_commons_futures_OSFuturePromiseImpl_a(env: JNIEnv, _: JObject, runnable: JObject) {
    let run_method_id: JMethodID = env.get_method_id("java/lang/Runnable", "run", "()V;").unwrap();
    let jvm = env.get_java_vm().unwrap();
    let runnable = env.new_global_ref(runnable).unwrap();
    thread::spawn(move || {
        let env = jvm.attach_current_thread().unwrap();
        env.call_method_unchecked(&runnable, run_method_id, ReturnType::Primitive(Primitive::Void), &[]).unwrap();
    });
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