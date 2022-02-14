package com.fgomes.trademap_clone.data

import android.util.Log
import com.fgomes.trademap_clone.domain.Acao
import com.google.gson.Gson
import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import java.util.*

class ConectorMqtt {

    lateinit var mqttClient: MqttClient

    fun start(acaoRecebida: (Acao) -> Unit ) {
        mqttClient = MqttClient(
            "tcp://test.mosquitto.org:1883",
            UUID.randomUUID().toString(),
            MemoryPersistence()
        )

        mqttClient.setCallback(object : MqttCallback {
            override fun connectionLost(cause: Throwable?) {
                conectarMqtt()
            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                if (message == null) return
                val src = String(message.payload)
                runCatching {
                    val acao = Gson().fromJson(src, Acao::class.java)
                    acaoRecebida(acao)
                }.onFailure {
                    Log.i("ConectorMqtt", it.message.orEmpty())
                }
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                Log.i("ConectorMqtt", "delivery")
            }
        })

        conectarMqtt()
    }

    private fun conectarMqtt() {
        runCatching {
            val connOpts = criarConfiguracao()
            mqttClient.connect(connOpts)
            mqttClient.subscribe("acao.b3.dados", 2)
            Log.i("Conector MQTT","Conectou")
        }.onFailure{
            Log.e("Conector MQTT","Não conectou")
        }
    }

    private fun criarConfiguracao() = MqttConnectOptions().apply {
            userName = "Android"
            keepAliveInterval = 5
            isCleanSession = true
            connectionTimeout = 30
        }

    fun desligar() {
        mqttClient.disconnect()
    }
}